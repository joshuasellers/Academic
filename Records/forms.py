from django import forms
from .models import Patient, Hospital, Doctor, Prescription, TestResults
from django.utils import timezone
from django.utils.translation import ugettext_lazy as _


class PrescriptionForm(forms.ModelForm):
    date = timezone.now().date()

    class Meta:
        model = Prescription
        fields = {'patient', 'drug', 'dosage', 'instructions', 'hospital'}


class TestResultForm(forms.ModelForm):
    class Meta:
        model = TestResults
        fields = ('test_name', 'patient', 'results', 'file', 'viewable',)
        labels = {
            'test_name': _('Test Name'),
            'patient': _('Patient'),
            'results': _('Results'),
            'file': _('Choose File'),
            'viewable': _('Viewable'),
        }