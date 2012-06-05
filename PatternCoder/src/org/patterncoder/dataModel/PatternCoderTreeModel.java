/* Copyright (C) 2005 - 2007 the patternCoder team, http://www.patterncoder.org

 This file is part of the patternCoder application

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.patterncoder.dataModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * The model for the JTree
 *
 * @author Florian Siebler
 */
public class PatternCoderTreeModel implements TreeModel
{
    /**
     * List saving the TreeModel Listener
     */
    private List<TreeModelListener> listener = new ArrayList<TreeModelListener>();
    /**
     * All categories of EnumPatterns
     */
    public final EnumPatterns[] CAT = EnumPatterns.values();
    /*
     * Root of tree
     */
    public final String ROOT = "patternCoder";

    @Override
    public Object getRoot()
    {
        return ROOT;
    }

    @Override
    public Object getChild(Object o, int i)
    {
        if (o == ROOT)
        {
            return CAT[i];
        }
        else
        {
            EnumPatterns tempCat = (EnumPatterns) o;
            return tempCat.get(i);
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object node)
    {
        if (parent == ROOT)
        {
            for (int i = 0; i < CAT.length; i++)
            {
                if (node == CAT[i])
                {
                    return i;
                }
            }
        }
        EnumPatterns tempNode = (EnumPatterns) node;
        return tempNode.getIndexOf(node);
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o)
    {
        //
    }

    @Override
    public int getChildCount(Object o)
    {
        if (o == ROOT)
        {
            return CAT.length;
        }
        else
        {
            EnumPatterns tempCat = (EnumPatterns) o;
            return tempCat.size();
        }
    }

    /**
     * If the node is an instance of Pattern or the node is a category with zero
     * patterns the node is a leaf.
     *
     * @param o The node to check
     * @return Returns true if node is a leaf
     */
    @Override
    public boolean isLeaf(Object o)
    {
        if (o.getClass() == Pattern.class)
        {
            return true;
        }
        else
        {
            if (o.getClass() == EnumPatterns.class)
            {
                EnumPatterns tempEnum = (EnumPatterns) o;
                if (tempEnum.size() == 0)
                {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl)
    {
        listener.add(tl);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl)
    {
        listener.remove(tl);
    }
}
