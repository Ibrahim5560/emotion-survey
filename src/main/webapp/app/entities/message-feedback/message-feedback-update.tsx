import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './message-feedback.reducer';
import { IMessageFeedback } from 'app/shared/model/message-feedback.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMessageFeedbackUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MessageFeedbackUpdate = (props: IMessageFeedbackUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { messageFeedbackEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/message-feedback' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...messageFeedbackEntity,
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
          <h2 id="emotionSurveyApp.messageFeedback.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.messageFeedback.home.createOrEditLabel">Create or edit a MessageFeedback</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : messageFeedbackEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="message-feedback-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="message-feedback-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="systemIdLabel" for="message-feedback-systemId">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.systemId">System Id</Translate>
                </Label>
                <AvField id="message-feedback-systemId" type="string" className="form-control" name="systemId" />
              </AvGroup>
              <AvGroup>
                <Label id="centerIdLabel" for="message-feedback-centerId">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.centerId">Center Id</Translate>
                </Label>
                <AvField id="message-feedback-centerId" type="string" className="form-control" name="centerId" />
              </AvGroup>
              <AvGroup>
                <Label id="systemServicesIdLabel" for="message-feedback-systemServicesId">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.systemServicesId">System Services Id</Translate>
                </Label>
                <AvField id="message-feedback-systemServicesId" type="string" className="form-control" name="systemServicesId" />
              </AvGroup>
              <AvGroup>
                <Label id="counterLabel" for="message-feedback-counter">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.counter">Counter</Translate>
                </Label>
                <AvField id="message-feedback-counter" type="string" className="form-control" name="counter" />
              </AvGroup>
              <AvGroup>
                <Label id="trsIdLabel" for="message-feedback-trsId">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.trsId">Trs Id</Translate>
                </Label>
                <AvField id="message-feedback-trsId" type="string" className="form-control" name="trsId" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="message-feedback-userId">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.userId">User Id</Translate>
                </Label>
                <AvField id="message-feedback-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="message-feedback-message">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.message">Message</Translate>
                </Label>
                <AvField id="message-feedback-message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="message-feedback-status">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.status">Status</Translate>
                </Label>
                <AvField id="message-feedback-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="feedbackLabel" for="message-feedback-feedback">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.feedback">Feedback</Translate>
                </Label>
                <AvField id="message-feedback-feedback" type="text" name="feedback" />
              </AvGroup>
              <AvGroup>
                <Label id="applicantNameLabel" for="message-feedback-applicantName">
                  <Translate contentKey="emotionSurveyApp.messageFeedback.applicantName">Applicant Name</Translate>
                </Label>
                <AvField id="message-feedback-applicantName" type="text" name="applicantName" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/message-feedback" replace color="info">
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
  messageFeedbackEntity: storeState.messageFeedback.entity,
  loading: storeState.messageFeedback.loading,
  updating: storeState.messageFeedback.updating,
  updateSuccess: storeState.messageFeedback.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MessageFeedbackUpdate);
