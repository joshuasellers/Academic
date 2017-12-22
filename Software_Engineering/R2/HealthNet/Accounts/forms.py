from django import forms
from django.contrib.auth.models import User
from .models import Patient, Hospital, Nurse, Doctor
from django.forms.extras.widgets import SelectDateWidget
from django.utils import timezone
from django.utils.translation import ugettext_lazy as _


class PatientForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ('username', 'password', 'first_name', 'last_name', 'email')

        labels = {
            'first_name': _('First Name'),
            'username': _('Username'),
            'password': _('Password'),
            'last_name': _('Last Name'),
            'email': _('Email'),
        }



class PatientInfoForm(forms.ModelForm):
    birthday = forms.DateField(widget=SelectDateWidget(years=range(timezone.now().year - 100, timezone.now().year)),
                               initial=timezone.now().date())

    class Meta:
        model = Patient
        fields = ('birthday', 'sex', 'address', 'phone_number', 'insurance',
                 'height_ft', 'height_inch', 'weight', 'medical_info', 'hospital', 'ice_name','ice_phone')

        labels = {
            'phone_number': _('Phone Number [(999) 999-9999]'),
            'insurance': _('Insurance Number [123456789]'),
            'height_ft': _('Height (ft.)'),
            'height_inch': _('Height (in.)'),
            'medical_Info': _('Medical Info'),
            'hospital': _('Hospital'),
            'ice_name': _('Emergency Contact Name'),
            'ice_phone': _('Emergency Contact Phone')
        }


class DoctorForm(forms.ModelForm):
    class Meta:
        model = Doctor
        fields = ('user', 'hospital', 'phone_number')


class NurseForm(forms.ModelForm):
    class Meta:
        model = Nurse
        fields = ('user', 'hospital', 'phone_number')


class HospitalForm(forms.ModelForm):
    class Meta:
        model = Hospital
        fields = ('name', 'location')


class EditBioForm(forms.ModelForm):
    birthday = forms.DateField(widget=SelectDateWidget(years=range(timezone.now().year - 100, timezone.now().year)),
                               initial=timezone.now().date())

    class Meta:
        model = Patient
        fields = ('birthday', 'address', 'phone_number', 'insurance', 'ice_name', 'ice_phone')


class EditMedInfoForm(forms.ModelForm):

    class Meta:
        model = Patient
        fields = ('sex','height_ft', 'height_inch', 'weight', 'medical_info')


class EmergencyAdmitOrTransferForm(forms.ModelForm):

    class Meta:
        model = Patient
        fields = ('admit',)


class DischargeForm(forms.ModelForm):

    class Meta:
        model = Patient
        fields = ('discharge',)


class TransferForm(forms.ModelForm):
    hospital = forms.ModelChoiceField(queryset=Hospital.objects.all(),empty_label=None)
    class Meta:
        model = Patient
        fields = ('hospital',)