import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './trash-company.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrashCompanyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trashCompanyEntity = useAppSelector(state => state.trashCompany.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trashCompanyDetailsHeading">TrashCompany</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trashCompanyEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{trashCompanyEntity.name}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{trashCompanyEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/trash-company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trash-company/${trashCompanyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrashCompanyDetail;
