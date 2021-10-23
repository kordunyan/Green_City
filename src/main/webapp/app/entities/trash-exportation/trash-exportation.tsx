import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './trash-exportation.reducer';
import { ITrashExportation } from 'app/shared/model/trash-exportation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrashExportation = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const trashExportationList = useAppSelector(state => state.trashExportation.entities);
  const loading = useAppSelector(state => state.trashExportation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="trash-exportation-heading" data-cy="TrashExportationHeading">
        Trash Exportations
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Trash Exportation
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trashExportationList && trashExportationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Weight</th>
                <th>Date</th>
                <th>Trash Type</th>
                <th>Action</th>
                <th>Is Wash</th>
                <th>Osbb</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trashExportationList.map((trashExportation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${trashExportation.id}`} color="link" size="sm">
                      {trashExportation.id}
                    </Button>
                  </td>
                  <td>{trashExportation.weight}</td>
                  <td>
                    {trashExportation.date ? <TextFormat type="date" value={trashExportation.date} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{trashExportation.trash_type}</td>
                  <td>{trashExportation.action}</td>
                  <td>{trashExportation.is_wash ? 'true' : 'false'}</td>
                  <td>{trashExportation.osbb ? <Link to={`osbb/${trashExportation.osbb.id}`}>{trashExportation.osbb.name}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${trashExportation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${trashExportation.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${trashExportation.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Trash Exportations found</div>
        )}
      </div>
    </div>
  );
};

export default TrashExportation;
