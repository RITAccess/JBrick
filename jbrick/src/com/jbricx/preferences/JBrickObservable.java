package com.jbricx.preferences;

import org.eclipse.jface.preference.PreferenceStore;

public interface JBrickObservable {
  void update(PreferenceStore store);
}
