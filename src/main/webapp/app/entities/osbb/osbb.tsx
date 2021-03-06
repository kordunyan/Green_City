import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './osbb.reducer';
import { IOsbb } from 'app/shared/model/osbb.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Osbb = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const osbbList = useAppSelector(state => state.osbb.entities);
  const loading = useAppSelector(state => state.osbb.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="osbb-heading" data-cy="OsbbHeading">
        Osbbs
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link
            to={`${match.url}/new`}
            className="btn btn-primary jh-create-entity mr-2"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Osbb
          </Link>
          <a
            href="http://localhost:8080/export-by-osbb-csv"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="file" />
            &nbsp; CSV
          </a>
        </div>
      </h2>
      <div className="table-responsive">
        {osbbList && osbbList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Address</th>
                <th>Geo</th>
                <th>Name</th>
                <th>Trash Company</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {osbbList.map((osbb, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${osbb.id}`} color="link" size="sm">
                      {osbb.id}
                    </Button>
                  </td>
                  <td>{osbb.address}</td>
                  <td>{osbb.geo}</td>
                  <td>{osbb.name}</td>
                  <td>{osbb.trashCompany ? <Link to={`trash-company/${osbb.trashCompany.id}`}>{osbb.trashCompany.name}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${osbb.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${osbb.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${osbb.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Osbbs found</div>
        )}
      </div>
    </div>
  );
};

export default Osbb;
