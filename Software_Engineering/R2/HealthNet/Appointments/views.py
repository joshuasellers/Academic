from django.shortcuts import render, redirect
from django.utils import timezone
from .models import Appointment
from django.http import HttpResponse
from .forms import PatientAppointmentForm, DoctorAppointmentForm, NurseAppointmentForm
from Accounts.models import Patient, Doctor, Nurse
from django.contrib.auth.decorators import login_required
import logging
log = logging.getLogger('Appointments')

# Create your views here.


@login_required()
def index(request):
    # display the list of appointment that the user can view
    current_user = request.user
    can_cancel = True
    num_patient = Patient.objects.filter(user=current_user).count()
    num_doctor = Doctor.objects.filter(user=current_user).count()
    num_nurse = Nurse.objects.filter(user=current_user).count()
    if num_patient != 0:
        patient = Patient.objects.get(user=current_user)
        appointments = Appointment.objects.filter(patient=patient).order_by('date')
    elif num_doctor != 0:
        doctor = Doctor.objects.get(user=current_user)
        appointments = Appointment.objects.filter(doctor=doctor).order_by('date')
    elif num_nurse != 0:
        appointments = Appointment.objects.all().order_by('date')
        can_cancel = False
    context = {'can_cancel': can_cancel,
               'appointments': appointments}
    return render(request, 'appt_index.html', context)


@login_required()
def create_appt(request):
    # check type of user
    patient = Patient.objects.filter(user=request.user).count()
    doctor = Doctor.objects.filter(user=request.user).count()
    nurse = Nurse.objects.filter(user=request.user).count()
    if request.method == "POST":
        if patient != 0:
            form = PatientAppointmentForm(request.POST)
        if doctor != 0:
            form = DoctorAppointmentForm(request.POST)
        if nurse != 0:
            form = NurseAppointmentForm(request.POST)
        if form.is_valid():
            if form.cleaned_data.get('date') < timezone.now().date():
                s = """ An appointment cannot be made for a past date."""
                log.info("User {0} attempted to create an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "create appointment"})
                return render(request, 'invalid.html', {'s': s})

            if patient != 0:
                patient_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                           time=form.cleaned_data.get('time'),
                                                           patient=Patient.objects.filter(user=request.user)).count()
            else:
                patient_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                           time=form.cleaned_data.get('time'),
                                                           patient=form.cleaned_data.get('patient')).count()
            if patient_check != 0:
                s = """The selected patient is not available at this time."""
                log.info("User {0} attempted to create an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "create appointment"})
                return render(request, 'invalid.html', {'s': s})

            if doctor != 0:
                doc_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                       time=form.cleaned_data.get('time'),
                                                       doctor=Doctor.objects.filter(user=request.user)).count()
            else:
                doc_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                       time=form.cleaned_data.get('time'),
                                                       doctor=form.cleaned_data.get('doctor')).count()
            if doc_check != 0:
                s = """The selected doctor is not available at this time."""
                log.info("User {0} attempted to create an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "create appointment"})
                return render(request, 'invalid.html', {'s': s})

            log.info("User {0} created an appointment.".format(request.user),
                     extra={'user': str(request.user), 'type': "create appointment"})
            appt = form.save(commit=False)
            form.save()
            if patient != 0:
                appt.patient = Patient.objects.get(user=request.user)
            if doctor != 0:
                appt.doctor = Doctor.objects.get(user=request.user)
            appt.save()
            return redirect('appt_index')
    else:

        if patient != 0:
            form = PatientAppointmentForm
        if doctor != 0:
            form = DoctorAppointmentForm
        if nurse != 0:
            form = NurseAppointmentForm
    return render(request, 'add.html', {'form': form})


@login_required()
def update_appt(request, pk):
    appt = Appointment.objects.get(pk=pk)
    # check type of user
    patient = Patient.objects.filter(user=request.user).count()
    doctor = Doctor.objects.filter(user=request.user).count()
    nurse = Nurse.objects.filter(user=request.user).count()
    if request.method == "POST":
        if patient != 0:
            # this brings up the old appointment form to edit
            form = PatientAppointmentForm(request.POST, instance=appt)
        elif doctor != 0:
            form = DoctorAppointmentForm(request.POST, instance=appt)
        elif nurse != 0:
            form = NurseAppointmentForm(request.POST, instance=appt)
        if form.is_valid():
            if form.cleaned_data.get('date') < timezone.now().date():
                s = """ An appointment cannot be made for a past date."""
                log.info("User {0} attempted to create an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "create appointment"})
                return render(request, 'invalid.html', {'s': s})
            if patient != 0:
                patient_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                           time=form.cleaned_data.get('time'),
                                                           patient=Patient.objects.filter(
                                                               user=request.user)).exclude(pk=pk).count()
            else:
                patient_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                           time=form.cleaned_data.get('time'),
                                                           patient=form.cleaned_data.get('patient')
                                                           ).exclude(pk=pk).count()
            if patient_check != 0:
                s = """The selected patient is not available at this time."""
                log.info("User {0} attempted to update an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "update appointment"})
                return render(request, 'invalid.html', {'s': s})

            if doctor != 0:
                doc_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                       time=form.cleaned_data.get('time'),
                                                       doctor=Doctor.objects.filter(user=request.user)
                                                       ).exclude(pk=pk).count()
            else:
                doc_check = Appointment.objects.filter(date=form.cleaned_data.get('date'),
                                                       time=form.cleaned_data.get('time'),
                                                       doctor=form.cleaned_data.get('doctor')).exclude(pk=pk).count()
            if doc_check != 0:
                s = """The selected doctor is not available at this time."""
                log.info("User {0} attempted to update an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "update appointment"})
                return render(request, 'invalid.html', {'s': s})

            log.info("User {0} updated an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "update appointment"})
            appt = form.save(commit=False)
            appt.save()
            return redirect('appt_index')
    else:
        if patient != 0:
            # this brings up the old appointment form to edit
            form = PatientAppointmentForm(instance=appt)
        elif doctor != 0:
            form = DoctorAppointmentForm(instance=appt)
        elif nurse != 0:
            form = NurseAppointmentForm(instance=appt)
    return render(request, 'update.html', {'form': form})


@login_required
def cancel_appt(request, pk):
    # deletes the instance of the appointment
    appt = Appointment.objects.get(pk=pk)
    appt.delete()

    # deletes the Appointment from the queryset
    Appointment.objects.filter(pk=pk).delete
    log.info("User {0} canceled an appointment.".format(request.user),
                         extra={'user': str(request.user), 'type': "cancel appointment"})
    return redirect('appt_index')
