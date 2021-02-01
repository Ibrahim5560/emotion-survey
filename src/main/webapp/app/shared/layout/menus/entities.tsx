import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/system">
      <Translate contentKey="global.menu.entities.system" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/system-services">
      <Translate contentKey="global.menu.entities.systemServices" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/center">
      <Translate contentKey="global.menu.entities.center" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/users">
      <Translate contentKey="global.menu.entities.users" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/messages">
      <Translate contentKey="global.menu.entities.messages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/message-feedback">
      <Translate contentKey="global.menu.entities.messageFeedback" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
