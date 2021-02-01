import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICenter } from 'app/shared/model/center.model';
import { getEntities as getCenters } from 'app/entities/center/center.reducer';
import { ISystem } from 'app/shared/model/system.model';
import { getEntities as getSystems } from 'app/entities/system/system.reducer';
import { ISystemServices } from 'app/shared/model/system-services.model';
import { getEntities as getSystemServices } from 'app/entities/system-services/system-services.reducer';
import { IUsers } from 'app/shared/model/users.model';
import { getEntities as getUsers } from 'app/entities/users/users.reducer';
import { getEntity, updateEntity, createEntity, reset } from './messages.reducer';
import { IMessages } from 'app/shared/model/messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MessagesUpdate = (props: IMessagesUpdateProps) => {
  const [centerId, setCenterId] = useState('0');
  const [systemId, setSystemId] = useState('0');
  const [systemServicesId, setSystemServicesId] = useState('0');
  const [usersId, setUsersId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { messagesEntity, centers, systems, systemServices, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/messages' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCenters();
    props.getSystems();
    props.getSystemServices();
    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...messagesEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emotionSurveyApp.messages.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.messages.home.createOrEditLabel">Create or edit a Messages</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : messagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="counterLabel" for="messages-counter">
                  <Translate contentKey="emotionSurveyApp.messages.counter">Counter</Translate>
                </Label>
                <AvField id="messages-counter" type="string" className="form-control" name="counter" />
              </AvGroup>
              <AvGroup>
                <Label id="trsIdLabel" for="messages-trsId">
                  <Translate contentKey="emotionSurveyApp.messages.trsId">Trs Id</Translate>
                </Label>
                <AvField id="messages-trsId" type="string" className="form-control" name="trsId" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="messages-userId">
                  <Translate contentKey="emotionSurveyApp.messages.userId">User Id</Translate>
                </Label>
                <AvField id="messages-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="messages-message">
                  <Translate contentKey="emotionSurveyApp.messages.message">Message</Translate>
                </Label>
                <AvField id="messages-message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="messages-status">
                  <Translate contentKey="emotionSurveyApp.messages.status">Status</Translate>
                </Label>
                <AvField id="messages-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="applicantNameLabel" for="messages-applicantName">
                  <Translate contentKey="emotionSurveyApp.messages.applicantName">Applicant Name</Translate>
                </Label>
                <AvField id="messages-applicantName" type="text" name="applicantName" />
              </AvGroup>
              <AvGroup>
                <Label for="messages-center">
                  <Translate contentKey="emotionSurveyApp.messages.center">Center</Translate>
                </Label>
                <AvInput id="messages-center" type="select" className="form-control" name="centerId">
                  <option value="" key="0" />
                  {centers
                    ? centers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="messages-system">
                  <Translate contentKey="emotionSurveyApp.messages.system">System</Translate>
                </Label>
                <AvInput id="messages-system" type="select" className="form-control" name="systemId">
                  <option value="" key="0" />
                  {systems
                    ? systems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="messages-systemServices">
                  <Translate contentKey="emotionSurveyApp.messages.systemServices">System Services</Translate>
                </Label>
                <AvInput id="messages-systemServices" type="select" className="form-control" name="systemServicesId">
                  <option value="" key="0" />
                  {systemServices
                    ? systemServices.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="messages-users">
                  <Translate contentKey="emotionSurveyApp.messages.users">Users</Translate>
                </Label>
                <AvInput id="messages-users" type="select" className="form-control" name="usersId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/messages" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  centers: storeState.center.entities,
  systems: storeState.system.entities,
  systemServices: storeState.systemServices.entities,
  users: storeState.users.entities,
  messagesEntity: storeState.messages.entity,
  loading: storeState.messages.loading,
  updating: storeState.messages.updating,
  updateSuccess: storeState.messages.updateSuccess,
});

const mapDispatchToProps = {
  getCenters,
  getSystems,
  getSystemServices,
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessagesUpdate);
