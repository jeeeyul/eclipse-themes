package net.jeeeyul.eclipse.themes;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class CSSClasses {
  private List<String> classes = new Function0<List<String>>() {
    public List<String> apply() {
      ArrayList<String> _arrayList = new ArrayList<String>();
      return _arrayList;
    }
  }.apply();
  
  public CSSClasses(final String source) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(source, null));
    if (!_notEquals) {
      _and = false;
    } else {
      String _trim = source.trim();
      boolean _isEmpty = _trim.isEmpty();
      boolean _not = (!_isEmpty);
      _and = (_notEquals && _not);
    }
    if (_and) {
      String[] _split = source.split(" ");
      Iterables.<String>addAll(this.classes, ((Iterable<? extends String>)Conversions.doWrapArray(_split)));
    }
  }
  
  public boolean add(final String className) {
    boolean _xifexpression = false;
    boolean _contains = this.classes.contains(className);
    if (_contains) {
      return false;
    } else {
      boolean _add = this.classes.add(className);
      _xifexpression = _add;
    }
    return _xifexpression;
  }
  
  public boolean remove(final String className) {
    boolean _remove = this.classes.remove(className);
    return _remove;
  }
  
  public String toString() {
    String _join = IterableExtensions.join(this.classes, " ");
    return _join;
  }
  
  public static void main(final String[] args) {
    CSSClasses _cSSClasses = new CSSClasses("a b c");
    InputOutput.<CSSClasses>println(_cSSClasses);
  }
}
