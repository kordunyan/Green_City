import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './empty-trash-images.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EmptyTrashImagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const emptyTrashImagesEntity = useAppSelector(state => state.emptyTrashImages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="emptyTrashImagesDetailsHeading">EmptyTrashImages</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{emptyTrashImagesEntity.id}</dd>
          <dt>
            <span id="path">Path</span>
          </dt>
          <dd>{emptyTrashImagesEntity.path}</dd>
          <dt>Trash Exportation</dt>
          <dd>{emptyTrashImagesEntity.trashExportation ? emptyTrashImagesEntity.trashExportation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/empty-trash-images" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/empty-trash-images/${emptyTrashImagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmptyTrashImagesDetail;
