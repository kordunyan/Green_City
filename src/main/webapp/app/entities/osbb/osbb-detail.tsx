import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './osbb.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OsbbDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const osbbEntity = useAppSelector(state => state.osbb.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="osbbDetailsHeading">Osbb</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{osbbEntity.id}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{osbbEntity.address}</dd>
          <dt>
            <span id="geo">Geo</span>
          </dt>
          <dd>{osbbEntity.geo}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{osbbEntity.name}</dd>
          <dt>Trash Company</dt>
          <dd>{osbbEntity.trashCompany ? osbbEntity.trashCompany.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/osbb" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/osbb/${osbbEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OsbbDetail;
