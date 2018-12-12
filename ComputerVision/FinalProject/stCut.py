import random


def BFS(s, t, vertices, adjacnecy_list):
    # This array is filled by BFS and to store path
    parent = []
    # Mark all the vertices as not visited
    visited = {}
    for v in vertices:
        visited[v] = False
    # Create a queue for BFS
    queue = []
    # Mark the source node as visited and enqueue it
    queue.append(vertices[s])
    visited[s] = True
    # Standard BFS Loop
    while queue:
        # Dequeue a vertex from queue and print it
        u = queue.pop()
        # Get all adjacent vertices of the dequeued vertex u
        # If a adjacent has not been visited, then mark it
        # visited and enqueue it
        for adjacent_v in adjacnecy_list[u]:
            v_b = adjacent_v[0]
            weight = adjacent_v[1]
            if (not visited[v_b]) and weight > 0:
                queue.append(v_b)
                visited[v_b] = True
                parent.append((v_b, weight))
    return visited[t], parent


def minCut(source, sink, vertices, adjacency_list):
    max_flow = 0  # There is no flow initially

    # Augment the flow while there is path from source to sink
    path_found = True
    while path_found:
        path_found, parent = BFS(source, sink, vertices, adjacency_list)

        # Find minimum residual capacity of the edges along the
        # path filled by BFS. Or we can say find the maximum flow
        # through the path found.
        path_flow = float("Inf")
        s = sink
        while s != source:
            path_flow = min(path_flow, graph[parent[s]][s])
            s = parent[s]

            # Add path flow to overall flow
        max_flow += path_flow

        # update residual capacities of the edges and reverse edges
        # along the p
        v = sink
        while (v != source):
            u = parent[v]
            self.graph[u][v] -= path_flow
            self.graph[v][u] += path_flow
            v = parent[v]

            # print the edges which initially had weights
    # but now have 0 weight
    for i in range(self.ROW):
        for j in range(self.COL):
            if self.graph[i][j] == 0 and self.org_graph[i][j] > 0:
                print str(i) + " - " + str(j)


def stmain(im):
    v_list, adjacency_list, width, height = im[0], im[1], im[2], im[3]
    s = random.randint(0, len(v_list)-1)
    same = True
    t = s
    while same:
        t = random.randint(0, len(v_list) - 1)
        if t != s and t != s -1 and t != s + 1:
            same = False
    minCut(s, t, v_list, adjacency_list)