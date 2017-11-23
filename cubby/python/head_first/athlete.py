def sanitize(time_string):
    if '-' in time_string:
        splitter='-'
    elif ':' in time_string:
        splitter=":"
    else:
        return(time_string)
    (mins, secs)=time_string.split(splitter)
    return(mins+"."+secs)

class Athlete:
    def __init__(self, a_name, a_dob=None, a_times=[]):
        self.name=a_name
        self.dob=a_dob
        self.times=a_times

    def top3(self):
        return(sorted(set([sanitize(t) for t in self.times]))[0:3])

    def add_time(self, a_time):
        self.times.append(sanitize(a_time))

    def add_times(self, a_times):
        self.times.extend([sanitize(t) for t in a_times])

def get_coach_data(filename):
    try:
        with open(filename) as f:
            data=f.readline()
        templ=data.strip().split(',')
        return(Athlete(templ.pop(0), templ.pop(0), templ))
    except IOError as ioerr:
        print('File error: '+str(ioerr))
        return(None)
