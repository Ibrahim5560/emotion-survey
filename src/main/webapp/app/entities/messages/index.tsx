import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Messages from './messages';
import MessagesDetail from './messages-detail';
import MessagesUpdate from './messages-update';
import MessagesDeleteDialog from './messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Messages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MessagesDeleteDialog} />
  </>
);

export default Routes;
