# -*- coding: utf-8 -*-
# Generated by Django 1.9.1 on 2016-10-19 17:40
from __future__ import unicode_literals

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Appointments', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='appointment',
            name='date',
            field=models.DateField(default=datetime.date(2016, 10, 19)),
        ),
    ]
