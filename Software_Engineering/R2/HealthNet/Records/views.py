from django.shortcuts import render, redirect
from .models import Prescription, TestResults
from django.http import HttpResponseRedirect
from .forms import PrescriptionForm, TestResultForm
from Accounts.models import Patient, Doctor, Nurse
from django.contrib.auth.decorators import login_required
from Accounts.views import landing_page
from django.conf import settings
from django.core.files.storage import FileSystemStorage
# Create your views here.


@login_required()
def upload_file(request):
    if request.method == "POST":
        form = TestResultForm(request.POST, request.FILES)
        if form.is_valid():
            test = form.save(commit=False)
            form.save()
            doctor = Doctor.objects.get(user=request.user)
            test.doctor = doctor
            test.save()
            return redirect(landing_page)
    else:
        form = TestResultForm()
    return render(request, 'upload.html', {'form':form})


@login_required()
def index(request):
    can_remove = False
    num_patient = Patient.objects.filter(user=request.user).count()
    num_doctor = Doctor.objects.filter(user=request.user).count()
    num_nurse = Nurse.objects.filter(user=request.user).count()
    if num_patient != 0:
        patient = Patient.objects.get(user=request.user)
        p_list = Prescription.objects.filter(patient=patient)
    elif num_doctor != 0:
        p_list = Prescription.objects.all()
        can_remove = True
    elif num_nurse != 0:
        nurse = Nurse.objects.get(user=request.user)
        p_list = Prescription.objects.filter(hospital=nurse.hospital)
    context = {'can_remove': can_remove,
               'p_list': p_list}
    return render(request, 'list.html', context)


@login_required()
def add_prescription(request):
    if request.method == "POST":
        #check to see if user is Doctor
        num_doctor = Doctor.objects.filter(user=request.user).count()
        if num_doctor != 0:
            form = PrescriptionForm(request.POST)
            if form.is_valid():
                #temp is a Prescription instance, but prescription is a long word
                temp = form.save(commit=False)
                form.save()
                temp.doctor = Doctor.objects.get(user=request.user)
                temp.save()
                return redirect('prescription_list')
    else:
        form = PrescriptionForm
    return render(request, 'add.html', {'form': form})


@login_required()
def remove_prescription(request, pk):
    p = Prescription.objects.filter(pk=pk)
    p.delete()
    Prescription.objects.filter(pk=pk).delete
    return redirect('prescription_list')


@login_required()
def view_test_results(request):
    num_patient = Patient.objects.filter(user=request.user).count()
    num_doctor = Doctor.objects.filter(user=request.user).count()
    can_edit = False
    if num_patient != 0:
        patient = Patient.objects.get(user=request.user)
        tests = TestResults.objects.filter(patient=patient)
    elif num_doctor != 0:
        can_edit = True
        doctor = Doctor.objects.get(user=request.user)
        tests = TestResults.objects.filter(doctor=doctor)
    context = {'tests': tests,
               'can_edit': can_edit}
    return render(request, 'tests.html', context)


@login_required()
def make_viewable(request, pk):
    p = TestResults.objects.filter(pk=pk)
    p.viewable = True
    return redirect('view_test_results')