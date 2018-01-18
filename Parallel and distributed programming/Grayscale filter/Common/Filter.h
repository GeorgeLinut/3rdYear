#ifndef FILTER_H_
#define FILTER_H_

const size_t filterSize = 3;
const size_t border = filterSize / 2;

const int32_t filter[filterSize][filterSize] =
{
    { 0, -1,  0 },
    { -1,  5, -1 },
    { 0, -1,  0 }
};

#endif // !FILTER_H_
