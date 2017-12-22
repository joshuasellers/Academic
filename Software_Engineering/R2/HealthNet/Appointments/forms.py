from django import forms
from .models import Appointment
from django.forms.extras.widgets import SelectDateWidget
from django.utils import timezone


class PatientAppointmentForm(forms.ModelForm):
    date = forms.DateField(widget=SelectDateWidget(years=range(timezone.now().year, timezone.now().year + 1)),
                           initial=timezone.now())

    class Meta:
        model = Appointment
        fields = {'doctor', 'hospital', 'date', 'time',}


class DoctorAppointmentForm(forms.ModelForm):
    date = forms.DateField(widget=SelectDateWidget(years=range(timezone.now().year, timezone.now().year + 2)),
                           initial=timezone.now())

    class Meta:
        model = Appointment
        fields = {'patient', 'hospital', 'date', 'time'}


class NurseAppointmentForm(forms.ModelForm):
    date = forms.DateField(widget=SelectDateWidget(years=range(timezone.now().year, timezone.now().year + 1)),
                           initial=timezone.now())

    class Meta:
        model = Appointment
        fields = {'doctor', 'patient', 'hospital', 'date', 'time'}



