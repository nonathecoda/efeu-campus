def init_orders(start_stations, end_stations, tws_early, tws_late):
    orders = []
    for i in range(len(start_stations)):
        orders.append(Order(i,"order"+str(i),start_stations[i], end_stations[i], 1, tws_early, tws_late[i], 1))
    return orders

def get_order_dict(orders):
    dict = {}
    dict["Order_ID"] = []
    dict["FROM"] = []
    dict["TO"] = []
    dict["EARLY"] = []
    dict["LATE"] = []
    dict["LOAD"] = []
    dict["SERVICE_TIME"] = []
    dict["TYPE"] = []
    for o in orders:
        dict["Order_ID"].append(o.id)
        dict["FROM"].append(o.pickup)
        dict["TO"].append(o.delivery)
        dict["EARLY"].append(o.early)
        dict["LATE"].append(o.late)
        dict["LOAD"].append(0)
        dict["SERVICE_TIME"].append(o.service_duration)
        dict["TYPE"].append("TRANSPORT")
    return dict

class Order(object):

    def __init__(self, _id, _description, _pickup_station, _delivery_station, _service_duration, _early, _late, _q):
        self.id = _id
        self.description = _description
        self.pickup = _pickup_station
        self.delivery = _delivery_station
        self.service_duration = _service_duration
        self.early = _early
        self.late = _late
        self.q = _q

    def jsonDefault(self):
        return object.__dict__

    def get_id(self):
        return self.id

if __name__ == "__main__":
    print("instance")

    #print(inst.toJson())
    print("instance done")