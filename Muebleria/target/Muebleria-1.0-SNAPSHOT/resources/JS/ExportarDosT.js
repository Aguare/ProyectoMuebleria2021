class TableCSVExporter {
    constructor(table, table2, includeHeaders = true) {
        this.table = table;
        this.table2 = table2;
        this.rows = Array.from(table.querySelectorAll("tr"));
        this.rows2 = Array.from(table2.querySelectorAll("tr"));

        if (!includeHeaders && this.rows[0].querySelectorAll("th").length) {
            this.rows.shift();
    }
    }

    convertToCSV() {
        const lines = [];
        const numCols = this._findLongestRowLength();

        for (const row of this.rows) {
            let line = "";
            for (let i = 0; i < numCols; i++) {
                if (row.children[i] !== undefined) {
                    line += TableCSVExporter.parseCell(row.children[i]);
                }
                line += (i !== (numCols - 1)) ? "," : "";
            }
            lines.push(line);
        }
        for (const row of this.rows2) {
            let line = "";
            for (let i = 0; i < numCols; i++) {
                if (row.children[i] !== undefined) {
                    line += TableCSVExporter.parseCell(row.children[i]);
                }
                line += (i !== (numCols - 1)) ? "," : "";
            }
            lines.push(line);
        }

        return lines.join("\n");
    }

    _findLongestRowLength() {
        return this.rows.reduce((l, row) => row.childElementCount > l ? row.childElementCount : l, 0);
    }

    static parseCell(tableCell) {
        let parsedValue = tableCell.textContent;
        // Replace all double quotes with two double quotes
        parsedValue = parsedValue.replace(/"/g, `""`);
        // If value contains comma, new-line or double-quote, enclose in double quotes
        parsedValue = /[",\n]/.test(parsedValue) ? `"${parsedValue}"` : parsedValue;
        return parsedValue;
    }
}
