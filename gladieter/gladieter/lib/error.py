class GladieterLibError(Exception):
    def __init__(self, msg):
        super(GladieterLibError, self).__init__(msg)
        self.msg = msg

    def __str__(self):
        return self.msg