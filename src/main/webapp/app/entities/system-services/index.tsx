import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SystemServices from './system-services';
import SystemServicesDetail from './system-services-detail';
import SystemServicesUpdate from './system-services-update';
import SystemServicesDeleteDialog from './system-services-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SystemServicesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SystemServicesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SystemServicesDetail} />
      <ErrorBoundaryRoute path={match.url} component={SystemServices} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SystemServicesDeleteDialog} />
  </>
);

export default Routes;
