import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MessageFeedback from './message-feedback';
import MessageFeedbackDetail from './message-feedback-detail';
import MessageFeedbackUpdate from './message-feedback-update';
import MessageFeedbackDeleteDialog from './message-feedback-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MessageFeedbackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MessageFeedbackUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MessageFeedbackDetail} />
      <ErrorBoundaryRoute path={match.url} component={MessageFeedback} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MessageFeedbackDeleteDialog} />
  </>
);

export default Routes;
