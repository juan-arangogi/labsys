/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { DetalleOrdenLabsysDetailComponent } from 'app/entities/detalle-orden-labsys/detalle-orden-labsys-detail.component';
import { DetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';

describe('Component Tests', () => {
    describe('DetalleOrdenLabsys Management Detail Component', () => {
        let comp: DetalleOrdenLabsysDetailComponent;
        let fixture: ComponentFixture<DetalleOrdenLabsysDetailComponent>;
        const route = ({ data: of({ detalleOrden: new DetalleOrdenLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [DetalleOrdenLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DetalleOrdenLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetalleOrdenLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.detalleOrden).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
