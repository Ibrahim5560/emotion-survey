import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './center.reducer';
import { ICenter } from 'app/shared/model/center.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICenterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CenterUpdate = (props: ICenterUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { centerEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/center' + props.location.search);
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
        ...centerEntity,
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
          <h2 id="emotionSurveyApp.center.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.center.home.createOrEditLabel">Create or edit a Center</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : centerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="center-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="center-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="center-nameAr">
                  <Translate contentKey="emotionSurveyApp.center.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="center-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="center-nameEn">
                  <Translate contentKey="emotionSurveyApp.center.nameEn">Name En</Translate>
                </Label>
                <AvField id="center-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="center-code">
                  <Translate contentKey="emotionSurveyApp.center.code">Code</Translate>
                </Label>
                <AvField id="center-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="center-status">
                  <Translate contentKey="emotionSurveyApp.center.status">Status</Translate>
                </Label>
                <AvField id="center-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/center" replace color="info">
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
  centerEntity: storeState.center.entity,
  loading: storeState.center.loading,
  updating: storeState.center.updating,
  updateSuccess: storeState.center.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CenterUpdate);
