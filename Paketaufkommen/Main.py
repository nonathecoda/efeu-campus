import cProfile
import numpy as np
import Instance
import Writer
import random


def run_main(_input, _params, _is_profiling):
    if _is_profiling:
        pr = cProfile.Profile()
        pr.enable()

    number_of_jobs = _params["number_of_jobs"]
    order_types = []
    start_stations = []
    end_stations = []
    tws_late = []

    # define order mode (synchronous, asynchronous)
    # not considered as not useful for late time window

    # define order types
    # not useful as pickup requires an early time window
    np.random.seed(_params["order_types_seed"]) #'seed' to use same random number as before
    for _ in range(number_of_jobs):
        order_types.append(weighted_choice(_params["order_types"], _params["order_types_weights"], True))

    # define stations
    # (assumption: single depot)
    depotId = _params["depots"][0]
    stationIds = _params["stations_async"]
    np.random.seed(_params["stations_seed"])
    for i in range(number_of_jobs):
        stationId = weighted_choice(stationIds, _params["stations_async_weights"], True)
        if order_types[i] == "delivery":
            start_stations.append(depotId)
            end_stations.append(stationId)
        else:
            start_stations.append(stationId)
            end_stations.append(depotId)

    # define tw
    np.random.seed(_params["time_range_seed"])
    time_start = _params["time_range_in_min"][0]
    time_end = _params["time_range_in_min"][1]
    if (_params["is_equally_distributed_in_time"]):
        tws_late = np.random.randint(time_start, time_end + 1, number_of_jobs) #random.randint(low, high(exclusive), size)
    else:
        # Normalverteilung
        random.seed(_params["time_range_seed"])
        distr_avg = _params["time_distribution_normal"][0]
        distr_standard_deviation = _params["time_distribution_normal"][1]
        while len(tws_late) < number_of_jobs:
            value = int(random.normalvariate(distr_avg, distr_standard_deviation))
            if time_start <= value <= time_end:
                tws_late.append(value)
        # Alternativ: ggf. Poisson-Verteilung

    # output
    output_file = "../InstanceGeneratorLight/Output/" + "o" + str(len(_params["order_types"])) \
                  + "s" + str(_params["order_types_seed"]) + "_" \
                  + "d" + str(len(_params["depots"])) + "_" \
                  + "a" + str(len(stationIds)) \
                  + "s" + str(_params["stations_seed"]) + "_" \
                  + "n" + str(number_of_jobs) + "_" \
                  + "t" + str(_params["time_range_in_min"][0]) + "t" + str(_params["time_range_in_min"][1]) \
                  + "s" + str(_params["time_range_seed"]) + "_" \
                  + "e" + str(_params["is_equally_distributed_in_time"]) + "_" \
                  + "JOBS.csv"
    print(output_file)
    tws_early = 480 #8 o'clock
    orders = Instance.init_orders(start_stations, end_stations, tws_early, tws_late)
    Writer.print_infos(Instance.get_order_dict(orders), output_file)

    if _is_profiling:
        pr.disable()
        pr.print_stats(sort='time')


# https://www.python-kurs.eu/python_numpy_wahrscheinlichkeit.php
def find_interval(x, partition):
    for i in range(0, len(partition)):
        if x < partition[i]:
            return i - 1
    return -1


# https://www.python-kurs.eu/python_numpy_wahrscheinlichkeit.php
def weighted_choice(sequence, weights, isReturnElement):
    x = np.random.random() #random floats in the half-open interval [0, 1)
    cum_weights = [0] + list(np.cumsum(weights)) #cumulative sum of the elements
    index = find_interval(x, cum_weights)
    if (isReturnElement):
        return sequence[index]
    else:
        return index


if __name__ == "__main__":
    print("main")

    isProfiling = False

    inst = None

    params = {
        'number_of_jobs': 100,
        'depots': ["d1"],
        'stations_async': ["a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10"],
        'stations_async_weights': [1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10, 1 / 10],
        'stations_seed': 42,
        'order_types': ["delivery", "pickup"],
        'order_types_weights': [0.9, 0.1],
        'order_types_seed': 42,
        'time_range_in_min': [720, 900],  # [0]: start of period (12 o'clock), [1]: end of period (15 o'clock); 0..1440 (0-24 o'clock)
        'time_range_seed': 42,
        'is_equally_distributed_in_time': False,  # otherwise time_distribution is chosen
        'time_distribution_normal': [820, 60],  # [0] avg., [1] standard deviation
        'output': ""
    }

    run_main(inst, params, isProfiling)

    print("main done")
