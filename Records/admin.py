from django.contrib import admin
from .models import Prescription, TestResults

# Register your models here.
admin.site.register(Prescription)
admin.site.register(TestResults)

