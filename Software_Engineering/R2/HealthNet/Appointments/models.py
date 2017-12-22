from django.db import models
from django.utils import timezone
from Accounts.models import Patient, Doctor, Hospital
# Create your models here.


class Appointment(models.Model):
    TIMES = [
        ('8:00 am', '8:00 am'),
        ('8:30 am', '8:30 am'),
        ('9:00 am', '9:00 am'),
        ('9:30 am', '9:30 am'),
        ('10:00 am', '10:00 am'),
        ('10:30 am', '10:30 am'),
        ('11:00 am', '11:00 am'),
        ('11:30 am', '11:30 am'),
        ('12:00 pm', '12:00 pm'),
        ('12:30 pm', '12:30 pm'),
        ('1:00 pm', '1:00 pm'),
        ('1:30 pm', '1:30 pm'),
        ('2:00 pm', '2:00 pm'),
        ('2:30 pm', '2:30 pm'),
        ('3:00 pm', '3:00 pm'),
        ('3:30 pm', '3:30 pm'),
        ('4:00 pm', '4:00 pm'),
        ('4:30 pm', '4:30 pm'),
        ('5:00 pm', '5:00 pm'),
    ]

    patient = models.ForeignKey(Patient, null=True, blank=False)
    doctor = models.ForeignKey(Doctor, null=True, blank=False)
    hospital = models.ForeignKey(Hospital, null=True,blank=False
)
    date = models.DateField(default=timezone.now)
    time = models.CharField(max_length=8, choices=TIMES, default='8:00')

    def __str__(self):
        return 'Dr. {0} has an appointment with {1} on {2} at {3}'.format(self.doctor, self.patient,
                                                                          self.date, self.time)
