from .forms import PatientForm, PatientInfoForm, EditBioForm, EditMedInfoForm, EmergencyAdmitOrTransferForm, \
    DischargeForm, TransferForm
from .models import Patient, Nurse, Doctor
from django.shortcuts import render, redirect, HttpResponse, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import Group, User
from django.contrib.auth.signals import user_logged_in, user_logged_out, user_login_failed
import logging
from django.template import loader, Context

log = logging.getLogger('Accounts')


# Code for logging login/logout stuff.
def log_login(sender, user, request, **kwargs):
    log.info("User {0} logged in.".format(user), extra={'user': str(user), 'type': "login"})


def log_logout(sender, user, request, **kwargs):
    log.info("User {0} logged out.".format(user), extra={'user': str(user), 'type': "logout"})


def log_logfail(sender, credentials, **kwargs):
    log.info("Failed login attempt.", extra={'user': 0, 'type': "login"})


user_logged_in.connect(log_login)
user_logged_out.connect(log_logout)
user_login_failed.connect(log_logfail)


# Create your views here.

# View user will see when first accessing website
def index(request):
    return render(request, 'index.html')


# View that a user will see when registering as a patient
def register(request):
    registered = False

    # If it's a HTTP POST, we're interested in processing form data.
    if request.method == 'POST':
        user_form = PatientForm(data=request.POST)
        profile_form = PatientInfoForm(data=request.POST)

        if user_form.is_valid() and profile_form.is_valid():
            # if forms are valid, save the user's form data to the database.
            user = user_form.save()

            user.set_password(user.password)
            user.save()

            profile = profile_form.save(commit=False)
            profile.user = user

            profile.save()
            # sets the Group of the user as Patient
            group = Group.objects.get(name='Patient')
            # adds user to the group Patient
            group.user_set.add(user)

            # Update our variable to tell the template registration was successful.
            # registered = True

            new_user = authenticate(username=user_form.cleaned_data['username'],
                                    password=user_form.cleaned_data['password'],
                                    )
            login(request, new_user)
            log.info("New patient {0} registered.".format(user), extra={'user': str(user), 'type': "register"})
            return HttpResponseRedirect('/')
        # print errors in form to user
        else:
            log.info("Patient attempted registration with erroneous fields.", extra={'user': 0, 'type': "register"})
            print(user_form.errors, profile_form.errors)

    else:
        user_form = PatientForm()
        profile_form = PatientInfoForm()

    context = {'patient_form': user_form, 'info_form': profile_form, 'registered': registered}
    return render(request, 'register.html', context)


def login_view(request):
    if request.method == 'POST':
        username = request.POST['username']
        password = request.POST['password']
        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                # Redirect to a success page.
                return redirect('landing_page')
            else:
                # Return a 'disabled account' error message
                return HttpResponse("This HealthNet account is disabled.")
        else:
            # Return an 'invalid login' error message.
            return HttpResponse("Invalid login details.")
    else:
        return render(request, "login.html")


# decorator saying that the user needs to be logged in to view this page
@login_required()
def logout_view(request):
    logout(request)
    return HttpResponseRedirect('/')


@login_required()
def landing_page(request):
    # Used to direct the user to the appropriate homepage
    current_user = request.user
    patient = Patient.objects.filter(user=current_user)
    num_patients = patient.count()
    doctor = Doctor.objects.filter(user=current_user)
    num_doctors = doctor.count()
    nurse = Nurse.objects.filter(user=current_user)
    num_nurses = nurse.count()
    if num_patients != 0:
        log.info('Patient ' + str(current_user) + ' has visited the landing page.', extra={'user': str(current_user),
                                                                                           'type': "homepage"})
        # patient = Patient.objects.get(user=current_user)
        return redirect('patient_home')
    elif num_doctors != 0:
        log.info('Doctor ' + str(current_user) + ' has visited the landing page.', extra={'user': str(current_user),
                                                                                          'type': "homepage"})
        # doctor = Doctor.objects.get(user=current_user)
        return redirect('doctor_home')
    elif num_nurses != 0:
        log.info('Nurse ' + str(current_user) + ' has visited the landing page.', extra={'user': str(current_user),
                                                                                         'type': "homepage"})
        # nurse = Nurse.objects.get(user=current_user)
        return redirect('nurse_home')
    else:
        return HttpResponseRedirect('/admin/')


@login_required()
def patient_homepage(request):
    patient = Patient.objects.get(user=request.user)
    return render(request, 'patient_home.html',
                  {'patient': patient, 'name': patient.user.first_name, 'Patient_id': patient.user.username})


@login_required()
def doctor_homepage(request):
    # The page the doctor will see after logged in
    doctor = Doctor.objects.get(user=request.user)
    patient_list = Patient.objects.all().filter(doctor=doctor)
    for patient in patient_list:
        if patient.admit:
                patient.hospital = Doctor.objects.get(user=request.user).hospital
                patient.save()
        if patient.discharge:
                patient.hospital = None
                patient.save()
        patient.admit = False
        patient.discharge = False
        patient.save()
    y = 0
    context = {'doctor': doctor,
               'patients': patient_list,
               'start': y}
    return render(request, 'doctor_home.html', context)


@login_required()
def nurse_homepage(request):
    # The page the nurse will see after logged in
    nurse = Nurse.objects.get(user=request.user)
    patient_list = Patient.objects.all().filter(nurse=nurse)
    for patient in patient_list:
        if patient.admit:
                patient.hospital = Nurse.objects.get(user=request.user).hospital
                patient.save()
        patient.admit = False
        patient.save()
    context = {'nurse': nurse,
               'patients': patient_list}
    return render(request, 'nurse_home.html', context)


@login_required()
def patients(request):
    # The page the nurse/doctor will use to see all the patients
    try:
        user = Nurse.objects.get(user=request.user)
        backwards = "http://127.0.0.1:8000/accounts/nurse_home/"
        patient_list = Patient.objects.all().filter(nurse=user)
    except Nurse.DoesNotExist:
        user = Doctor.objects.get(user=request.user)
        backwards = "http://127.0.0.1:8000/accounts/doctor_home/"
        patient_list = Patient.objects.all()
    context = {'patients': patient_list, 'person': user, 'type': backwards}
    return render(request, 'patients.html', context)


@login_required()
def view_bio(request):
    patient = Patient.objects.get(user=request.user)
    log.info("Patient {0} has visited their bio.".format(patient), extra={'user': str(request.user), 'type': "bio visit"})
    return render(request, 'view_bio.html', {'patient': patient})


@login_required()
def edit_bio_info(request):
    patient = Patient.objects.get(user=request.user)
    if request.method == "POST":
        bio_form = EditBioForm(request.POST, instance=patient)
        if bio_form.is_valid():
            log.info("Patient {0} has updated their bio.".format(patient), extra={'user': str(request.user),
                                                                                  'type':"bio edit"})
            bio_form.save()
            return redirect('view_bio')
    else:
        log.info("Patient {0} attempted to update their bio.".format(patient), extra={'user': str(request.user),
                                                                                      'type': "bio edit"})
        bio_form = EditBioForm(instance=patient)

    return render(request, 'edit_bio_info.html', {'bio_form': bio_form,
                                                  'patient': patient})


@login_required()
def edit_med_info(request, pk):
    patient = Patient.objects.get(pk=pk)
    # check type of user
    doctor = Doctor.objects.filter(user=request.user).count()
    nurse = Nurse.objects.filter(user=request.user).count()
    back = ""
    if doctor != 0:
        back = "http://127.0.0.1:8000/accounts/doctor_home/"
    else:
        back = "http://127.0.0.1:8000/accounts/nurse_home/"
    if request.method == "POST":
        form = EditMedInfoForm(request.POST, instance=patient)
        if form.is_valid():
            p = form.save(commit=False)
            p.save()
            if doctor != 0:
                log.info("Doctor {0} updated patient info for {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "patient update"})
                return redirect('doctor_home')
            else:
                log.info("Nurse {0} updated patient info for {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "patient update"})
                return redirect('nurse_home')
        else:
            log.info("User {0} failed to update patient info for {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "patient update"})
            print("Invalid Update")
    else:
        form = EditMedInfoForm(instance=patient)
    return render(request, 'edit_med_info.html', {'form': form, 'patient': patient, 'back': back})


@login_required()
def admit(request, pk):
    patient = Patient.objects.get(pk=pk)
    # check type of user
    doctor = Doctor.objects.filter(user=request.user).count()
    nurse = Nurse.objects.filter(user=request.user).count()
    back = ""
    if doctor != 0:
        back = "http://127.0.0.1:8000/accounts/doctor_home/"
    else:
        back = "http://127.0.0.1:8000/accounts/nurse_home/"
    if patient.hospital is None and patient.admit == False:
        if request.method == "POST":
            if nurse != 0:
                u = Nurse.objects.get(user=request.user)
            else:
                u = Doctor.objects.get(user=request.user)
            form = EmergencyAdmitOrTransferForm(request.POST, instance=patient)
            if form.is_valid():
                p = form.save(commit=False)
                p.save()
                if doctor != 0:
                    log.info("Doctor {0} admitted patient {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "admit"})
                    return redirect('doctor_home')
                else:
                    log.info("Nurse {0} admitted patient {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "admit"})
                    return redirect('nurse_home')
            else:
                log.info("User {0} failed to admit patient {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "admit"})
                print("Invalid Update")
        else:
            if nurse != 0:
                u = Nurse.objects.get(user=request.user)
            else:
                u = Doctor.objects.get(user=request.user)
            form = EmergencyAdmitOrTransferForm(instance=patient)
        return render(request, 'admit.html', {'form': form, 'patient': patient, 'back': back, 'user':u})
    else:
        return redirect(back)


@login_required()
def discharge(request, pk):
    patient = Patient.objects.get(pk=pk)
    # check type of user
    back = ""
    back = "http://127.0.0.1:8000/accounts/doctor_home/"
    if patient.hospital is not None:
        if request.method == "POST":
            u = Doctor.objects.get(user=request.user)
            form = DischargeForm(request.POST, instance=patient)
            if form.is_valid():
                p = form.save(commit=False)
                p.save()
                log.info("Doctor {0} discharged patient {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "discharge"})
                return redirect('doctor_home')
            else:
                log.info("User {0} failed to discharge patient {1}.".format(request.user, patient),
                         extra={'user': str(request.user), 'type': "discharge"})
                print("Invalid Update")
        else:
            u = Doctor.objects.get(user=request.user)
            form = DischargeForm(instance=patient)
        return render(request, 'discharge.html', {'form': form, 'patient': patient, 'back': back, 'user':u})
    else:
        return redirect(back)


@login_required()
def contact(request):
    patients = Patient.objects.all()
    doctors = Doctor.objects.all()
    nurses = Nurse.objects.all()
    context = {'patients': patients, 'doctors': doctors,'nurses': nurses}
    return render(request, 'contacts.html', context)


@login_required()
def transfer(request, pk):
    patient = Patient.objects.get(pk=pk)
    # check type of user
    doctor = Doctor.objects.filter(user=request.user).count()
    nurse = Nurse.objects.filter(user=request.user).count()
    if nurse != 0:
        user = Nurse.objects.get(user = request.user)
    else:
        user = Doctor.objects.get(user = request.user)
    back = ""
    if doctor != 0:
        back = "http://127.0.0.1:8000/accounts/doctor_home/"
        b = "doctor_home.html"
    else:
        back = "http://127.0.0.1:8000/accounts/nurse_home/"
        b = "nurse_home.html"
    if request.method == "POST":
        form = TransferForm(request.POST, instance=patient)
        if form.is_valid() and form.has_changed():
            p = form.save(commit=False)
            p.save()
            if doctor != 0:
                log.info("Doctor {0} transferred patient {1} to {2}.".format(request.user, patient,
                        form.cleaned_data['hospital']), extra={'user': str(request.user), 'type': "transfer"})
                return redirect('doctor_home')
            else:
                log.info("Nurse {0} transferred patient {1} to {2}.".format(request.user, patient,
                        form.cleaned_data['hospital']), extra={'user': str(request.user), 'type': "transfer"})
                return redirect('nurse_home')
        else:
            log.info("User {0} failed to transfer patient {1}.".format(request.user, patient),
                     extra={'user': str(request.user), 'type': "transfer"})
    else:
        form = TransferForm(instance=patient)
    return render(request, 'transfer.html', {'form': form, 'patient': patient, 'back': back})


def some_view(request):
    # Create the HttpResponse object with the appropriate CSV header.
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="patient_info.csv"'
    patient = Patient.objects.get(user=request.user)
    csv_data = (
        ('Patient:', patient.__str__()),
        ('Height:', patient.height_ft, 'feet', patient.height_inch, 'inches'),
        ('Weight:', patient.weight, 'lbs'),
        ('Birthday:', patient.birthday),
        ('Phone Number:', patient.phone_number),
        ('Address:', patient.address),
        ('Insurance:', patient.insurance),
        ('Hospital:', patient.hospital),
        ('Doctor:', patient.doctor),
        ('Nurse:', patient.nurse),
        ('Emergency Contact:', patient.ice_name),
        ('Emergency Phone Number:', patient.ice_phone),
    )

    t = loader.get_template('patient_info.txt')
    c = Context({
        'data': csv_data,
    })
    response.write(t.render(c))
    return response
