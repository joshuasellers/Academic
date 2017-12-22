from django.db import models
from django.contrib.auth.models import Group
from django.core.validators import RegexValidator
# Create your models here.


class Hospital(models.Model):
    name = models.CharField(max_length=100)
    location = models.CharField(max_length=300)

    def __str__(self):
        return self.name


class Nurse(models.Model):
    user = models.ForeignKey('auth.User')
    phone_regex = RegexValidator(regex=r'^\(?\d{3}\)?[ -]\d{3}-\d{4}$', message="Phone number must be entered in the format: '(999) 999-9999'. 10 digits are required.")
    phone_number = models.PositiveIntegerField(default=None, null=True, blank=True)
    hospital = models.ForeignKey(Hospital, default=None, null=True)
    group = Group.objects.get(name='Nurse')

    def __str__(self):
        return self.user.get_full_name()


class Doctor(models.Model):
    user = models.ForeignKey('auth.User')
    phone_regex = RegexValidator(regex=r'^\(?\d{3}\)?[ -]\d{3}-\d{4}$', message="Phone number must be entered in the format: '(999) 999-9999'. 10 digits are required.")
    phone_number = models.PositiveIntegerField(default=None, null=True, blank=True)
    hospital = models.ForeignKey(Hospital, default=None, null=True)
    group = Group.objects.get(name='Doctor')

    def __str__(self):
        return self.user.get_full_name()


class Patient(models.Model):
    user = models.ForeignKey('auth.User')
    #####
    birthday = models.DateField(default=None, null=True)
    #####
    address_regex = RegexValidator(regex='^(\w|\s)+$', message ="Address does not match accepted format; no special characters allowed")
    address = models.CharField(max_length=100,validators=[address_regex], default="")
    #####
    phone_regex = RegexValidator(regex=r'^\(?\d{3}\)?[ -]\d{3}-\d{4}$', message="Phone number must be entered in the format: '(999) 999-9999'. 10 digits are required.")
    phone_number = models.CharField(max_length=14, validators=[phone_regex], blank=True, null = True)
    #####
    weight_regex = RegexValidator(regex=r'^\d{1,3}$', message="Weight must be entered in the format: '999'")
    weight = models.CharField(max_length=3,validators=[weight_regex], default="")
    #####
    ft_regex = RegexValidator(regex=r'^\d{1}$', message="Height must be entered in the format: '9'")
    height_ft = models.CharField(max_length=1,validators=[ft_regex], default="")
    #####
    in_regex = RegexValidator(regex=r'^\d{1,2}$', message="Height must be entered in the format: '99'")
    height_inch = models.CharField(max_length=2,validators=[in_regex], default="")
    #####
    sex = models.CharField(max_length=6, choices = [('Male', 'MALE'), ('Female', 'FEMALE'), ('Other', 'OTHER')])
    #####
    insurance_regex = RegexValidator(regex=r'^\d{9}$', message="Insurance number must be entered in the format: '999999999'. Only to 9 digits allowed.")
    insurance = models.CharField(max_length=9, validators=[insurance_regex], blank=True, null = True)
    #####
    medical_info = models.CharField(max_length=500, blank=True, null=True)
    #####
    ice_name_regex = RegexValidator(regex=r'^[a-zA-Z]+\s[a-zA-Z]+$', message ="Name does not match accepted format; no special characters allowed")
    ice_name = models.CharField(max_length=100, validators=[ice_name_regex], null = True)
    #####
    ice_phone = models.CharField(max_length=16,validators=[phone_regex], null = True)
    #####
    doctor = models.ForeignKey(Doctor, default=None, blank=True, null=True)
    #####
    nurse = models.ForeignKey(Nurse, default=None, blank=True, null=True)
    #####
    hospital = models.ForeignKey(Hospital, default=None, blank=True, null=True)
    #####
    admit = models.BooleanField(default=False)
    discharge = models.BooleanField(default=False)

    group = Group.objects.get(name='Patient')

    def __str__(self):
        return self.user.get_full_name()


class Admin(models.Model):
    user = models.ForeignKey('auth.User')
    group = Group.objects.get(name='Admin')

    def __str__(self):
        return self.user.get_username()


