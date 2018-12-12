import os
from load_images import load_images
from preprocess import preprocessNotPlanar, preprocessPlanar
from stCut import stmain
import numpy as np
import cv2


#for s in SUBJECTS:
#    dir_path = os.path.dirname(os.path.realpath(__file__))
#    if not os.path.exists(dir_path + "/" + "figs" + "/" + s):
#        os.makedirs(dir_path + "/" + "figs" + "/" + s)
#    else:
#        shutil.rmtree(dir_path + "/" + "figs" + "/" + s)
#        os.makedirs(dir_path + "/" + "figs" + "/" + s)


if __name__ == '__main__':

    #1. read in the image array
    imarray = load_images()


    #2. preprocess the data - for each image, set up vertex list and edge list
    #   set weights for edges based on chosen afinity
    #   representation is planar or complete
    v_list, adjacency_list, width, height = preprocessNotPlanar(imarray[0])
    im = [v_list, adjacency_list, width, height]
    # TODO run s-t to segment image
    #  fix bfs and algorithm
    # get number of iterations
    # return segments per iteration
    # get random set of points for each iteration
    stmain(im)


    # TODO run normalized cut to segment
    # TODO run planar separator (time permitting)
