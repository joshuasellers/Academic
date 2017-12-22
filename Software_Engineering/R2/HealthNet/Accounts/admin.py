from django.contrib import admin
from .models import Patient, Hospital, Nurse, Doctor, Admin
# Register your models here.

admin.site.register(Patient)
admin.site.register(Hospital)
admin.site.register(Nurse)
admin.site.register(Doctor)
admin.site.register(Admin)