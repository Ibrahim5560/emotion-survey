import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './message-feedback.reducer';
import { IMessageFeedback } from 'app/shared/model/message-feedback.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMessageFeedbackDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MessageFeedbackDetail = (props: IMessageFeedbackDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { messageFeedbackEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.messageFeedback.detail.title">MessageFeedback</Translate> [
          <b>{messageFeedbackEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="systemId">
              <Translate contentKey="emotionSurveyApp.messageFeedback.systemId">System Id</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.systemId}</dd>
          <dt>
            <span id="centerId">
              <Translate contentKey="emotionSurveyApp.messageFeedback.centerId">Center Id</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.centerId}</dd>
          <dt>
            <span id="systemServicesId">
              <Translate contentKey="emotionSurveyApp.messageFeedback.systemServicesId">System Services Id</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.systemServicesId}</dd>
          <dt>
            <span id="counter">
              <Translate contentKey="emotionSurveyApp.messageFeedback.counter">Counter</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.counter}</dd>
          <dt>
            <span id="trsId">
              <Translate contentKey="emotionSurveyApp.messageFeedback.trsId">Trs Id</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.trsId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="emotionSurveyApp.messageFeedback.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.userId}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="emotionSurveyApp.messageFeedback.message">Message</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.message}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.messageFeedback.status">Status</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.status}</dd>
          <dt>
            <span id="feedback">
              <Translate contentKey="emotionSurveyApp.messageFeedback.feedback">Feedback</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.feedback}</dd>
          <dt>
            <span id="applicantName">
              <Translate contentKey="emotionSurveyApp.messageFeedback.applicantName">Applicant Name</Translate>
            </span>
          </dt>
          <dd>{messageFeedbackEntity.applicantName}</dd>
        </dl>
        <Button tag={Link} to="/message-feedback" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/message-feedback/${messageFeedbackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ messageFeedback }: IRootState) => ({
  messageFeedbackEntity: messageFeedback.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessageFeedbackDetail);
