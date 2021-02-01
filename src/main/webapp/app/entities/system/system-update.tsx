import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './system.reducer';
import { ISystem } from 'app/shared/model/system.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISystemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemUpdate = (props: ISystemUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { systemEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system' + props.location.search);
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
        ...systemEntity,
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
          <h2 id="emotionSurveyApp.system.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.system.home.createOrEditLabel">Create or edit a System</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : systemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="system-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="system-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="system-nameAr">
                  <Translate contentKey="emotionSurveyApp.system.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="system-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="system-nameEn">
                  <Translate contentKey="emotionSurveyApp.system.nameEn">Name En</Translate>
                </Label>
                <AvField id="system-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="system-code">
                  <Translate contentKey="emotionSurveyApp.system.code">Code</Translate>
                </Label>
                <AvField id="system-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="system-status">
                  <Translate contentKey="emotionSurveyApp.system.status">Status</Translate>
                </Label>
                <AvField id="system-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/system" replace color="info">
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
  systemEntity: storeState.system.entity,
  loading: storeState.system.loading,
  updating: storeState.system.updating,
  updateSuccess: storeState.system.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemUpdate);
