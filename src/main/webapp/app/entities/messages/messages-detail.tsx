import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './messages.reducer';
import { IMessages } from 'app/shared/model/messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMessagesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MessagesDetail = (props: IMessagesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { messagesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.messages.detail.title">Messages</Translate> [<b>{messagesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="counter">
              <Translate contentKey="emotionSurveyApp.messages.counter">Counter</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.counter}</dd>
          <dt>
            <span id="trsId">
              <Translate contentKey="emotionSurveyApp.messages.trsId">Trs Id</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.trsId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="emotionSurveyApp.messages.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.userId}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="emotionSurveyApp.messages.message">Message</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.message}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.messages.status">Status</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.status}</dd>
          <dt>
            <span id="applicantName">
              <Translate contentKey="emotionSurveyApp.messages.applicantName">Applicant Name</Translate>
            </span>
          </dt>
          <dd>{messagesEntity.applicantName}</dd>
          <dt>
            <Translate contentKey="emotionSurveyApp.messages.center">Center</Translate>
          </dt>
          <dd>{messagesEntity.centerId ? messagesEntity.centerId : ''}</dd>
          <dt>
            <Translate contentKey="emotionSurveyApp.messages.system">System</Translate>
          </dt>
          <dd>{messagesEntity.systemId ? messagesEntity.systemId : ''}</dd>
          <dt>
            <Translate contentKey="emotionSurveyApp.messages.systemServices">System Services</Translate>
          </dt>
          <dd>{messagesEntity.systemServicesId ? messagesEntity.systemServicesId : ''}</dd>
          <dt>
            <Translate contentKey="emotionSurveyApp.messages.users">Users</Translate>
          </dt>
          <dd>{messagesEntity.usersId ? messagesEntity.usersId : ''}</dd>
        </dl>
        <Button tag={Link} to="/messages" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/messages/${messagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ messages }: IRootState) => ({
  messagesEntity: messages.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessagesDetail);
