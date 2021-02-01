import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './system-services.reducer';
import { ISystemServices } from 'app/shared/model/system-services.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISystemServicesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemServicesDetail = (props: ISystemServicesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { systemServicesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="emotionSurveyApp.systemServices.detail.title">SystemServices</Translate> [<b>{systemServicesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nameAr">
              <Translate contentKey="emotionSurveyApp.systemServices.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{systemServicesEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="emotionSurveyApp.systemServices.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{systemServicesEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="emotionSurveyApp.systemServices.code">Code</Translate>
            </span>
          </dt>
          <dd>{systemServicesEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="emotionSurveyApp.systemServices.status">Status</Translate>
            </span>
          </dt>
          <dd>{systemServicesEntity.status}</dd>
          <dt>
            <Translate contentKey="emotionSurveyApp.systemServices.system">System</Translate>
          </dt>
          <dd>{systemServicesEntity.systemId ? systemServicesEntity.systemId : ''}</dd>
        </dl>
        <Button tag={Link} to="/system-services" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system-services/${systemServicesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ systemServices }: IRootState) => ({
  systemServicesEntity: systemServices.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemServicesDetail);
