from django.shortcuts import render
from django.contrib.admin.views.decorators import staff_member_required
import copy


# Create your views here.
@staff_member_required()
def logs_sort(request, sort):
    logfile = open("logfile")
    log = []
    for line in logfile:
        log.append(line.split('|'))
    log.reverse()  # Sort with newest activity first.
    if sort == 'user':
        u_log = sorted(log, key=lambda x: x[1].lower())  # Sort by user.
        full_log = []
        part_log = []
        for i in range(len(u_log)):  # Split into sub-lists by user.
            if (i > 0) and (u_log[i][1] != u_log[i - 1][1]):
                full_log.append(copy.deepcopy(part_log))  # Re-group sub-lists under mega-list.
                part_log = []
            part_log.append(u_log[i])
        full_log.append(copy.deepcopy(part_log))  # Ensure the last list is added.
        context = {'log': full_log, 'title': "Site Logs by User", 'sort': sort}
    elif sort == 'type':
        t_log = sorted(log, key=lambda x: x[2])  # Sort by type.
        full_log = []
        part_log = []
        for i in range(len(t_log)):  # Split into sub-lists by type.
            if (i > 0) and (t_log[i][2] != t_log[i - 1][2]):
                full_log.append(copy.deepcopy(part_log))  # Re-group sub-lists under mega-list.
                part_log = []
            part_log.append(t_log[i])
        full_log.append(copy.deepcopy(part_log))  # Ensure the last list is added.
        context = {'log': full_log, 'title': "Site Logs by Type", 'sort': sort}
    else:
        context = {'log': log, 'title': "Site Logs by Time", 'sort': sort}
    return render(request, 'logs.html', context)


@staff_member_required()
def logs(request):
    return logs_sort(request, "")
