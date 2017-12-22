from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^register/', views.register, name = 'register'),
    url(r'^profile/$', views.landing_page, name = 'landing_page'),
    #url(r'^login/$', views.login_view, name = 'login'),
    url(r'^login/$', 'django.contrib.auth.views.login',
       {'template_name': 'login.html'}),
    url(r'^logout/$', 'django.contrib.auth.views.logout',
       {'template_name': 'logout.html'}),
    url(r'^nurse_home/$', views.nurse_homepage, name = 'nurse_home'),
    url(r'^doctor_home/$',views.doctor_homepage, name = 'doctor_home'),
    url(r'^patient_home/$',views.patient_homepage, name = 'patient_home'),
    url(r'^edit_bio/$', views.edit_bio_info, name = 'edit_bio_info'),
    url(r'^view_bio/$', views.view_bio, name = 'view_bio'),
    url(r'^patients/$', views.patients, name = 'patients'),
    url(r'^contact/$', views.contact, name = 'contact'),
    url(r'^(?P<pk>[0-9]+)/edit_med_info/$', views.edit_med_info, name='edit_med_info'),
    url(r'^(?P<pk>[0-9]+)/admit/$', views.admit, name='admit'),
    url(r'^(?P<pk>[0-9]+)/discharge/$', views.discharge, name='discharge'),
    url(r'^(?P<pk>[0-9]+)/transfer/$', views.transfer, name='transfer'),
    url(r'^some_view/$', views.some_view, name = 'some_view'),

]
