package cluedo;



public final class Position {
	private int row; // must be between 0 and 25
	private int col; // must be between 0 and 26

	public Position( int row, int col) {
		this.row = row;
		this.col = col;

	}

	public int row() {
		return row;
	}

	public int column() {
		return col;
	}

	public boolean isValid() {
		return col >= 0 && col <= 26 && row >= 0 && row <= 25;
	}

	public boolean equals(Object o) {
		if(o instanceof Position) {
			Position p = (Position) o;
			return row == p.row && col == p.col;
		}
		return false;
	}

	public int hashCode() {
		return row ^ col;
	}

	public String toString() {
		return ((char)('a'+(col-1))) + Integer.toString(row);
	}


}
