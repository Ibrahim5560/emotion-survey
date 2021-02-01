import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISystem } from 'app/shared/model/system.model';
import { getEntities as getSystems } from 'app/entities/system/system.reducer';
import { getEntity, updateEntity, createEntity, reset } from './system-services.reducer';
import { ISystemServices } from 'app/shared/model/system-services.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISystemServicesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemServicesUpdate = (props: ISystemServicesUpdateProps) => {
  const [systemId, setSystemId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { systemServicesEntity, systems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system-services' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSystems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...systemServicesEntity,
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
          <h2 id="emotionSurveyApp.systemServices.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.systemServices.home.createOrEditLabel">Create or edit a SystemServices</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : systemServicesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="system-services-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="system-services-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="system-services-nameAr">
                  <Translate contentKey="emotionSurveyApp.systemServices.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="system-services-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="system-services-nameEn">
                  <Translate contentKey="emotionSurveyApp.systemServices.nameEn">Name En</Translate>
                </Label>
                <AvField id="system-services-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="system-services-code">
                  <Translate contentKey="emotionSurveyApp.systemServices.code">Code</Translate>
                </Label>
                <AvField id="system-services-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="system-services-status">
                  <Translate contentKey="emotionSurveyApp.systemServices.status">Status</Translate>
                </Label>
                <AvField id="system-services-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <AvGroup>
                <Label for="system-services-system">
                  <Translate contentKey="emotionSurveyApp.systemServices.system">System</Translate>
                </Label>
                <AvInput id="system-services-system" type="select" className="form-control" name="systemId">
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
              <Button tag={Link} id="cancel-save" to="/system-services" replace color="info">
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
  systems: storeState.system.entities,
  systemServicesEntity: storeState.systemServices.entity,
  loading: storeState.systemServices.loading,
  updating: storeState.systemServices.updating,
  updateSuccess: storeState.systemServices.updateSuccess,
});

const mapDispatchToProps = {
  getSystems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemServicesUpdate);
