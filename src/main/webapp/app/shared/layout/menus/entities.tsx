import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/osbb">
      Osbb
    </MenuItem>
    <MenuItem icon="asterisk" to="/trash-company">
      Trash Company
    </MenuItem>
    <MenuItem icon="asterisk" to="/trash-exportation">
      Trash Exportation
    </MenuItem>
    <MenuItem icon="asterisk" to="/full-trash-images">
      Full Trash Images
    </MenuItem>
    <MenuItem icon="asterisk" to="/empty-trash-images">
      Empty Trash Images
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
