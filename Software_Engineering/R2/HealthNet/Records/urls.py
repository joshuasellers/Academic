from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^prescription_list/', views.index, name = 'prescription_list'),
    url(r'^add_prescription/$', views.add_prescription, name = 'add_prescription'),
    url(r'^(?P<pk>[0-9]+)/remove/$', views.remove_prescription, name='remove_prescription'),
    url(r'^(?P<pk>[0-9]+)/release/$', views.make_viewable, name='make_viewable'),
    url(r'^upload/$', views.upload_file, name='upload_file'),
    url(r'^tests/$', views.view_test_results, name='view_test_results'),
    ]