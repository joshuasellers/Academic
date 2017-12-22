from django.db import models
from django.utils import timezone
from Accounts.models import Patient, Doctor, Hospital
# Create your models here.


class Prescription(models.Model):
    patient = models.ForeignKey(Patient, null=True, blank=False)
    doctor = models.ForeignKey(Doctor, null=True, blank=False)
    hospital = models.ForeignKey(Hospital, null=True, blank=False)
    date = models.DateField(default=timezone.now().date())
    drug = models.CharField(max_length=50, default="")
    dosage = models.CharField(max_length=20, default="")
    instructions = models.CharField(max_length=200, default="")

    def __str__(self):
        return '{0} prescribed {1} to {2} on {3}'.format(self.doctor, self.drug, self.patient, self.date)


class TestResults(models.Model):
    test_name = models.CharField(max_length=100, default="")
    patient = models.ForeignKey(Patient, null=True, blank=False)
    doctor = models.ForeignKey(Doctor, null=True, blank=False)
    file = models.FileField(upload_to='documents/', blank=True)
    results = models.CharField(max_length=250, default="")
    viewable = models.BooleanField(default=False)

    def __str__(self):
        return self.test_name
