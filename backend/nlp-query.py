#!/usr/bin/env python
# coding: utf-8

# In[3]:


import jieba
import sys
from sys import argv

def jiebacut(list):
    seg_list = ' '.join(jieba.cut_for_search(list))
    return seg_list
word=jiebacut(sys.argv[1])
output=word.split(' ')
print(output)

