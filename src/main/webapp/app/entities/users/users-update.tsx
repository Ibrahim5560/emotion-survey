import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './users.reducer';
import { IUsers } from 'app/shared/model/users.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUsersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UsersUpdate = (props: IUsersUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { usersEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/users' + props.location.search);
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
        ...usersEntity,
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
          <h2 id="emotionSurveyApp.users.home.createOrEditLabel">
            <Translate contentKey="emotionSurveyApp.users.home.createOrEditLabel">Create or edit a Users</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : usersEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="users-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="users-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameArLabel" for="users-nameAr">
                  <Translate contentKey="emotionSurveyApp.users.nameAr">Name Ar</Translate>
                </Label>
                <AvField id="users-nameAr" type="text" name="nameAr" />
              </AvGroup>
              <AvGroup>
                <Label id="nameEnLabel" for="users-nameEn">
                  <Translate contentKey="emotionSurveyApp.users.nameEn">Name En</Translate>
                </Label>
                <AvField id="users-nameEn" type="text" name="nameEn" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="users-code">
                  <Translate contentKey="emotionSurveyApp.users.code">Code</Translate>
                </Label>
                <AvField id="users-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="users-status">
                  <Translate contentKey="emotionSurveyApp.users.status">Status</Translate>
                </Label>
                <AvField id="users-status" type="string" className="form-control" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/users" replace color="info">
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
  usersEntity: storeState.users.entity,
  loading: storeState.users.loading,
  updating: storeState.users.updating,
  updateSuccess: storeState.users.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsersUpdate);
