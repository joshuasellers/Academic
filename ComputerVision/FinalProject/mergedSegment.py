from numpy import np
from itertools import chain


class Graph:

    def __init__(self, v_list, adjacency_list, width, height):
        """
        Initialize a graph
        :param v_list: list of vertices
        :param adjacency_list: adjacency list for each vertex
        :param width: width of image
        :param height: height of image
        """
        self.org_vlist = v_list
        self.org_adjacency_list = adjacency_list
        self.v_list = v_list
        self.adjacency_list = adjacency_list
        self.ROW = height
        self.COL = width


    def addtoA(self, A):
        """
        Add the tightest vertex to A
        :param A: current list of combined vertices
        :return: A + next vertex
        """
        con = -1
        b = (-1,-1)
        for v in self.v_list:
            if v in A:
                continue
            else:
                for a in A:
                    temp = 0
                    for e in self.adjacency_list[a]:
                        if e[0] == v:
                            temp += e[1]
                    if temp > con:
                        con = temp
                        b = v
        if b != (-1,-1):
            A.append(b)
            return A, con
        else:
            return A, con

    def getW(self, cut):
        """
        Get the weight of the current cut
        :param cut: list of vertices in cut
        :return: weight
        """
        w = 0
        for v in self.v_list:
            if v in cut:
                continue
            else:
                for c in cut:
                    for e in self.adjacency_list[c]:
                        if e[0] == v:
                            w += e[1]
        return w

    def getCut(self,A):
        """
        Return the cut (last thing in A)
        :param A: combined vertices
        :return: A[1:]
        """
        cut = A[:1]
        cutWeight = self.getW(cut)
        return cut, cutWeight

    def merge(self, A):
        """
        Merge the last two vertices in A
        :param A: combined vertices
        :return: N/A
        """
        c = chain.from_iterable(A[2:])
        new_v_list = []
        new_a_list = {}
        for v in self.v_list:
            if v in c:
                continue
            else:
                new_v_list.append(v)
        new_v_list.append(c)
        for i in range(0,len(new_v_list)-1):
            new_a_list[new_v_list[i]] = self.adjacency_list[new_v_list[i]]
        c_a_list = {}
        for v in c:
            for e in self.adjacency_list[v]:
                if e[0] in c_a_list:
                    c_a_list[e[0]] += e[1]
                else:
                    c_a_list[e[0]] = e[1]
        a = []
        for key in c_a_list:
            a.append([key, c_a_list[key]])
        new_a_list[c] = a
        self.adjacency_list = new_a_list
        self.v_list = new_v_list

                




    def minimumCutPhase(self, a):
        """
        Find the next minimum cut in G
        :param a: starting vertex
        :return: the cut and its weight
        """
        A = [a]
        while len(A) != len(self.v_list):
            A, con = self.addtoA(A)
        cutoutphase, cutWeight = self.getCut(A)
        self.merge(A)
        return cutoutphase, cutWeight


    def minimumCut(self, a):
        """
        Find minimum cut in a graph
        :param a: starting vertex
        :return: cut
        """
        mincut = float("inf")
        cut = []
        while len(self.v_list) > 1:
            cutphase, cutWeight = self.minimumCutPhase(a)
            if cutWeight < mincut:
                mincut = cutphase
                cut = cutphase
        return cut

def cutImage(im):
    """
    Run a minimum cut on an image
    :param im: image data
    :return: cut
    """
    v_list, adjacency_list, width, height = im[0], im[1], im[2], im[3]
    G = Graph(v_list,adjacency_list,width,height)
    return G.minimumCut(v_list[height/2][width/2])

def w(alist, v, a):
    """
    Find cumulative weight of edges between v and a
    :param alist:
    :param v:
    :param a:
    :return: sum of weight
    """
    s = 0
    for e in alist(a):
        if e[0] == v:
            s += e[1]
    return s


