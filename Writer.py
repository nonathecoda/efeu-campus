import pandas as pd

def write_data_frame_to_csv(df, filename):
    df.to_csv(filename, header=True, index=False, sep=';', decimal=',')

def write_dict_to_csv(dict, filename):
    df = pd.DataFrame(data=dict)
    write_data_frame_to_csv(df, filename)

def print_infos(orders_dict, filename):
    write_dict_to_csv(orders_dict, filename)

if __name__ == "__main__":
    print("Writer")

    print("Writer done")