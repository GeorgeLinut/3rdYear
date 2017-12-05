#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <mutex> 

using namespace std;

const int maxn = 15;


const int NR_THREADS = 4000;

int n, m;
vector <int> edge[maxn];
int T = 0;
mutex mtx;

inline void readGraph(string filename) {
	ifstream fin(filename);
	fin >> n >> m;
	for (int i = 0; i < m; ++i) {
		int x, y;
		fin >> x >> y;
		edge[x].push_back(y);
	}
}

inline bool isEdge(int x, int y) {
	for (auto it : edge[x]) {
		if (it == y) {
			return true;
		}
	}
	return false;
}

inline bool threadWork(int node, vector <int> &sol, int x) {
	sol.push_back(node);
	x |= (1 << node);
	if (sol.size() == n) {
		return isEdge(sol.back(), sol[0]);
	}
	vector <int> a(sol), b(sol);
	bool sol1 = 0;
	mtx.lock();
	if (T < NR_THREADS) {
		T += 2;
		mtx.unlock();
		thread t1([&]() {
			for (int i = 0; i < edge[node].size(); i += 2) {
				vector <int> aux(a);
				if (x & (1 << edge[node][i]))
					continue;
				if (threadWork(edge[node][i], aux, x)) {
					sol1 = 1;
					a = aux;
					return;
				}
			}
		});
		bool sol2 = 0;
		thread t2([&]() {
			for (int i = 1; i < edge[node].size(); i += 2) {
				vector <int> bux(b);
				if (x & (1 << edge[node][i]))
					continue;
				if (threadWork(edge[node][i], bux, x)) {
					sol2 = 1;
					b = bux;
					return;
				}
			}
		});
		t1.join();
		t2.join();
		mtx.lock();
		T -= 2;
		mtx.unlock();
		if (sol1) {
			sol = a;
			return 1;
		}
		else if (sol2) {
			sol = b;
			return 1;
		}
	}
	else { // do it on current thread
		mtx.unlock();
		for (int i = 0; i < edge[node].size(); i++) {
			vector <int> bux(b);
			if (x & (1 << edge[node][i]))
				continue;
			if (threadWork(edge[node][i], bux, x)) {
				sol = bux;
				return 1;
			}
		}
	}
	return 0;
}

int main() {
	readGraph("cycle.in");
	clock_t t;
	t = clock();
	vector <int> sol;
	bool ok = threadWork(1, sol, 0);
	cerr << n << '\n';
	cerr << (ok ? "Found" : "Not found") << '\n';
	if (ok) {
		for (auto it : sol) {
			cerr << it << ' ';
		}
		cerr << '\n';
	}
	t = clock() - t;
	cout << "Finding the hamiltonian cycle of " << n << " nodes took " << t
		<< " cycles (" << static_cast<float> (t) / CLOCKS_PER_SEC << " seconds)\n";
	return 0;
}