import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import System from './system';
import SystemDetail from './system-detail';
import SystemUpdate from './system-update';
import SystemDeleteDialog from './system-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SystemDetail} />
      <ErrorBoundaryRoute path={match.url} component={System} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SystemDeleteDialog} />
  </>
);

export default Routes;
