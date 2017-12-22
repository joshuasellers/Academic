# -*- coding: utf-8 -*-
# Generated by Django 1.9.1 on 2016-11-04 18:32
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Appointments', '0005_auto_20161104_1352'),
    ]

    operations = [
        migrations.AlterField(
            model_name='appointment',
            name='time',
            field=models.CharField(choices=[('8:00', '8:00 am'), ('8:30', '8:30 am'), ('9:00', '9:00 am'), ('9:30', '9:30 am'), ('10:00', '10:00 am'), ('10:30', '10:30 am'), ('11:00', '11:00 am'), ('11:30', '11:30 am'), ('12:00', '12:00 pm'), ('12:30', '12:30 pm'), ('1:00', '1:00 pm'), ('1:30', '1:30 pm'), ('2:00', '2:00 pm'), ('2:30', '2:30 pm'), ('3:00', '3:00 pm'), ('3:30', '3:30 pm'), ('4:00', '4:00 pm'), ('4:30', '4:30 pm'), ('5:00', '5:00 pm')], default='8:00', max_length=5),
        ),
    ]
