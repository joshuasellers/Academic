import os
import cv2


IMG_DIR = 'BSDS300/images/test'


def load_images():
    dir_path = os.path.dirname(os.path.realpath(__file__))
    # create array of images
    img_array = []
    for filename in os.listdir(dir_path +"/" + IMG_DIR):
        f = dir_path +"/" + IMG_DIR + "/" + filename
        image = cv2.imread(f, -1)
        #gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        img_array.append(image)

    return img_array
