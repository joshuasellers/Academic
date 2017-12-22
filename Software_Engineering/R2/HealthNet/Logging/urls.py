from django.conf.urls import url
from . import views


urlpatterns = [
    url(r'^$', views.logs, name='log_index'),
    url(r'^(user|type)$', views.logs_sort, name='log_sorted'),

]