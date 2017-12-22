from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^appt_index/', views.index, name = 'appt_index'),
    url(r'^new/$', views.create_appt, name = 'create_appt'),
    url(r'^(?P<pk>[0-9]+)/update/$', views.update_appt, name='update_appt'),
    url(r'^(?P<pk>[0-9]+)/cancel/$', views.cancel_appt, name='cancel_appt'),
    ]