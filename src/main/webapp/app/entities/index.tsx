import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import System from './system';
import SystemServices from './system-services';
import Center from './center';
import Users from './users';
import Messages from './messages';
import MessageFeedback from './message-feedback';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}system`} component={System} />
      <ErrorBoundaryRoute path={`${match.url}system-services`} component={SystemServices} />
      <ErrorBoundaryRoute path={`${match.url}center`} component={Center} />
      <ErrorBoundaryRoute path={`${match.url}users`} component={Users} />
      <ErrorBoundaryRoute path={`${match.url}messages`} component={Messages} />
      <ErrorBoundaryRoute path={`${match.url}message-feedback`} component={MessageFeedback} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
